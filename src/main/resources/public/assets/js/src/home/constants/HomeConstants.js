
module.exports = {
  IMAGES : "/assets/images/home/svg",
  PNG_IMAGES : "/assets/images/home/png",
  NOTIFICATION_TITLE_LENGTH: 50,
  CACHE_SIZE: 20,
  LOCALES: ["en", "el", "de", "es"],
  COUNTRIES: ["United Kingdom", "Spain", "Greece" ],
  TIMEZONES: [
    "Europe/London",
    "Europe/Madrid",
    "Europe/Athens"
  ],
  MESSAGE_TYPES:[
    {type: "ALERT"}, 
    {type: "RECOMMENDATION_STATIC"}, 
    {type: "RECOMMENDATION_DYNAMIC"}, 
    {type: "ANNOUNCEMENT"}
  ],
  MAIN_MENU: [{
    name: "dashboard",
    title: "section.dashboard",
    image: "dashboard-menu.svg",
    route:"/dashboard",
    children: []
  },
  {
    name: "history",
    title: "section.history",
    image: "stats-menu.svg",
    route:"/history",
  },
  {
    name: "messages",
    title: "section.notifications",
    image: "notifications-menu.svg",
    route: "/notifications",
  },
    
      {
        name: "settings",
        title: "section.settings",
        image: "settings-menu.svg",
        route:"/settings",
      },
  ],
  METER_AGG_METRICS: [
      {id:'devName',  mu:'', title:'history.device', icon: 'amphiro_small.svg', details: 'history.durationDetails', clickable: false}, 
      {id:'count',mu:'', title:'history.count', details:'history.countDetails', icon: 'default-ranking.svg', clickable: true},  
      {id:'difference', mu:'lt',title:'history.volume', details:'history.volumeDetails', icon: 'volume.svg',clickable: true},
  ],
  SHOWER_METRICS: [
      {id:'devName',  mu:'', title:'history.device', icon: 'amphiro_small.svg', details: 'history.durationDetails', clickable: false}, 
      {id:'volume', mu:'lt',title:'history.volume', details:'history.volumeDetails', icon:'volume.svg', clickable: true}, 
      {id:'temperature', mu:'ºC', title:'history.temperature', details: 'history.temperatureDetails',icon: 'temperature.svg', clickable: true}, 
      {id:'energy',mu:'W', title:'history.energy', details: 'history.energyDetails', icon:'energy.svg', clickable: true}, 
      {id:'friendlyDuration', mu:'', title:'history.duration', details: 'history.durationDetails', icon:'duration.svg', clickable: false}, 
  ],
  METER_PERIODS: [
    {id: 'day', title: 'periods.day'},
    {id: 'week', title: 'periods.week'},
    {id: 'month', title: 'periods.month'},
    {id: 'year', title: 'periods.year'},
    {id: 'custom', title: 'periods.custom'},
  ],
  DEV_PERIODS: [
    {id: 'ten', title: 'periods.ten'},
    {id: 'twenty', title: 'periods.twenty'},
    {id: 'fifty', title: 'periods.fifty'},
  ],
  METER_METRICS: [
    {id:'difference', title:'Volume'},
  ],
  DEV_METRICS: [
    {id:'volume', title:'Volume'},
    {id:'energy', title:'Energy'},
    {id:'duration', title:'Duration'},
    {id:'temperature', title:'Temperature'}
  ],
  METER_SORT: [
    {id: 'timestamp', title: 'Time'}, 
    {id:'difference', title: 'Volume'}
  ],
  DEV_SORT: [
    {id: 'id', title: 'ID'}, 
    {id: 'timestamp', title: 'Time'}, 
    {id:'volume', title: 'Volume'}, 
    {id:'devName', title: 'Device'}, 
    {id: 'energy', title: 'Energy'}, 
    {id:'temperature', title:'Temperature'}, 
    {id:'duration', title: 'Duration'}
  ],
  STATBOX_DISPLAYS: [
    {id: 'stat', title: 'Stat'}, 
    {id: 'chart', title: 'Chart'}
  ],
  STATIC_RECOMMENDATIONS: [
    {
       "id": 1,
        "title": "Consider checking for leaks in your home. A small water leak can go undetected for years, increase your water bill and potentially cause damage to your home",
       "description": "Checking for leaks is really simple, and you should do it once every 2 months. It is easier to check for leaks at night, before everyone goes to sleep. Make sure all water taps and fixtures are closed and check the reading in your water meter. First thing in the morning, check again. If you see the same reading, everything is good! If not, it means there may be a small leak somewhere in your house.",
       "category": 1,
       "image": null,
       "imageLink": null,
       "prompt": null,
       "externaLink": null,
       "unread": true
    },
          {
        "id": 2,
        "title": "Don’t forget to fix your dripping taps",
     "description": "A single dripping tap can lead to annual water losses of hundreds of liters. Why not save this water and use your savings to buy concert tickets instead?",
        "category": 1,
        "image": null,
        "imageLink": null,
        "prompt": null,
        "externaLink": null
    },
          {
       "id": 8,
       "title": "Consider purchasing an eco-efficient washing machine",
       "description": "Modern washing machines are intelligent enough to use the optimal amount of water and energy required for each load. Replacing your old washing machine with an eco-efficient one can save you more than 300 Euros in energy and water costs over its life.",
       "category": 2,
       "image":
"iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAFOUlEQVR4nO2czXXqMBBGXUJKyNJKNpSQEiiBEuggehVQQkrIEuyNS6AESnAJvIVlkOUZ68+SxmS+c7TJEUb4Wp6LZ+GgRALAyEWBkIsDIRYGAixMBBiYSDEwkCIhYEQCwMhFgZCLAyEWBgIsTAQYmEgxMJAiIWBEAsDIRYrEDH88xRu+ZoVCLeCjYEQawyEWPsPFf2Nkva9TvUAAAAASUVORK5CYII=",
       "imageLink": "/assets/images/Area Chart-100.png",
       "prompt": "Would you like to check out a list of water efficient washing machines?",
       "externaLink": null
    }
  ]
};

