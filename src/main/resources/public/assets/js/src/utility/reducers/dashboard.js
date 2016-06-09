var moment = require('moment');

var types = require('../constants/DashboardActionTypes');

var _createMapInitialState = function() {
  return {
    query : null,
    areas : null,
    meters : null,
    devices : null,
    features : null,
    interval : [
        moment().subtract(7, 'day'), moment()
    ],
  };
};

var _createInitialState = function() {
  return {
    isLoading : false,
    map : _createMapInitialState()
  };
};

var _extractTimeline = function(meters, areas) {
  var timeline = {}, timestamp, label;

  for (var m = 0; m < meters.length; m++) {
    var meter = meters[m];

    for (var p = 0; p < meter.points.length; p++) {
      var point = meter.points[p];

      timeline[point.timestamp] = timeline[point.timestamp] || {};
      timestamp = timeline[point.timestamp];

      timestamp[meter.label] = timestamp[meter.label] || {};
      label = timestamp[meter.label];

      label[meter.areaId] = label[meter.areaId] || 0;
      label[meter.areaId] += point.volume.SUM;
    }
  }

  timeline.getAreas = function() {
    return areas;
  };

  timeline.getTimestamps = function() {
    var values = [];
    for ( var timestamp in this) {
      var value = Number(timestamp);
      if (!isNaN(value)) {
        values.push(value);
      }
    }

    return values.sort(function(t1, t2) {
      if (t1 < t2) {
        return -1;
      }
      if (t1 > t2) {
        return 1;
      }
      return 0;
    });
  };

  timeline.getFeatures = function(timestamp, label) {
    var geojson = {
      type : 'FeatureCollection',
      features : [],
      crs : {
        type : 'name',
        properties : {
          name : 'urn:ogc:def:crs:OGC:1.3:CRS84'
        }
      }
    };

    if (!timestamp) {
      var timestamps = this.getTimestamps();
      if (timestamps.length > 0) {
        timestamp = timestamps[0];
        console.log(timestamp);
      } else {
        return geojson;
      }
    }
    if (!label) {
      if (Object.keys(this[timestamp])) {
        label = Object.keys(this[timestamp])[0];
        console.log(label);
      } else {
        return geojson;
      }
    }

    if (!this[timestamp]) {
      return geojson;
    }
    var instance = this[timestamp][label];

    if (!instance) {
      return geojson;
    }

    var areas = this.getAreas();

    for ( var index in instance) {
      geojson.features.push({
        'type' : 'Feature',
        'geometry' : areas[index].geometry,
        'properties' : {
          'label' : areas[index].label,
          'value' : instance[index]
        }
      });
    }

    console.log(geojson);
    return geojson;
  };

  return timeline;
};

var mapReducer = function(state, action) {
  switch (action.type) {
    case types.TIMELINE_REQUEST:
      return Object.assign({}, state, {
        query : action.query,
        areas : null,
        meters : null,
        devices : null,
        timeline : null,
        features : null
      });

    case types.TIMELINE_RESPONSE:
      if (action.success) {
        return Object.assign({}, state, {
          areas : action.data.areas,
          meters : action.data.meters,
          devices : action.data.devices,
          timeline : _extractTimeline(action.data.meters, action.data.areas),
          features : null
        });
      }

      return Object.assign({}, state, {
        areas : null,
        meters : null,
        devices : null,
        regions : null,
        features : null
      });

    case types.GET_FEATURES:
      console.log('GET_FEATURES');
      var features = (state.timeline ? state.timeline.getFeatures(action.timestamp, action.label) : null);

      return Object.assign({}, state, {
        features : features
      });

    default:
      return state || _createMapInitialState();
  }
};

var dashboard = function(state, action) {
  console.log(action.type);
  switch (action.type) {
    case types.TIMELINE_REQUEST:
      return Object.assign({}, state, {
        isLoading : true,
        map : mapReducer(state.map, action)
      });

    case types.TIMELINE_RESPONSE:
      if (action.success) {
        return Object.assign({}, state, {
          isLoading : false,
          map : mapReducer(state.map, action)
        });
      }

      return Object.assign({}, state, {
        isLoading : false,
        map : mapReducer(state.map, action)
      });

    case types.GET_FEATURES:
      return Object.assign({}, state, {
        isLoading : false,
        map : mapReducer(state.map, action)
      });

    case types.USER_RECEIVED_LOGOUT:
      return _createInitialState();

    default:
      return state || _createInitialState();
  }
};

module.exports = dashboard;