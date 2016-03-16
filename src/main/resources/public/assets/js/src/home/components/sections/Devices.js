var React = require('react');
var bs = require('react-bootstrap');
var connect = require('react-redux').connect;
var injectIntl = require('react-intl').injectIntl;
var { FormattedMessage } = require('react-intl');

var MainSection = require('../MainSection');


var Device = React.createClass({

  render: function() {
    const _t = this.props.intl.formatMessage;
    return (
      <div className="col-xs-5" >
        
        <bs.Input type="text" label={_t({id:"devices.name"})} defaultValue={this.props.name} ref="name" />
        <bs.Input type="text" label={_t({id:"devices.key"})} defaultValue={this.props.deviceKey} readOnly={true} />
          {(() => {
            if (this.props.type === 'AMPHIRO') {
              return (
                <bs.Input type="text" label={_t({id:"devices.mac"})} defaultValue={this.props.macAddress} readOnly={true} />
                
                );
            }
            else if (this.props.type === 'METER') {
              return (
                <bs.Input type="text" label={_t({id:"devices.serial"})} defaultValue={this.props.serial} readOnly={true} />
                );
            }
          })()}
          <hr />  
          <h4><FormattedMessage id="devices.properties" /></h4>
            {
              this.props.properties.map(function(property){
                if (!property.key){
                  return (<div/>);
                }
                return (
                  <bs.Input key={property.key} type="text" label={_t({id:`devices.${property.key}`})} defaultValue={property.value} readOnly={true} />
                  );
                })
            }
      </div>
    );
  }
});



var DevicesForm = React.createClass({
  
  onSubmit: function(e) {
    e.preventDefault();
    //HomeActions.updateProfile(this.state.profile);
  },

  render: function() {
    const devices = this.props.devices;
    const _t = this.props.intl.formatMessage;
    return (
      <form>
        <bs.Accordion className="col-xs-10">
          {
            devices.map(function(device, i){
              return (
                <bs.Panel key={device.deviceKey}
                  header={device.name || device.deviceKey}
                  eventKey={i}>
                  <Device {...device} intl={this.props.intl}/>
                </bs.Panel>
                );
            }.bind(this))
          }
        <bs.ButtonInput style={{marginTop: "20px"}} type="submit" value={_t({id:"forms.submit"})} onClick={this.onSubmit} />
        </bs.Accordion>
      </form>
    );
  }
});


var Devices = React.createClass({

  render: function() {
    return (
      <MainSection id="section.devices">
        <DevicesForm {...this.props} /> 
      </MainSection>
    );
  }
});

function mapStateToProps(state) {
  return {
    devices: state.user.profile.devices
  };
}

Devices = connect(mapStateToProps)(Devices);
Devices = injectIntl(Devices);
module.exports = Devices;