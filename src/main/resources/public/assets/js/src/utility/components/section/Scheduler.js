var React = require('react');
var ReactDOM = require('react-dom');
var Bootstrap = require('react-bootstrap');
var { Link } = require('react-router');
var Breadcrumb = require('../Breadcrumb');
var Table = require('../Table');
var RadialChart = require('../RadialChart');

var Scheduler = React.createClass({
	contextTypes: {
	    intl: React.PropTypes.object
	},
	
	getInitialState() {
		return {
			key: 1
    	};
	},

	selectSection(key) {
		this.setState({key : key});
  	},
	
  	render: function() {
  		var _t = this.context.intl.formatMessage;

  		var jobs = {
			fields: [{
				name: 'id',
				hidden: true
			}, {
				name: 'type',
				title: 'Type'
			}, {
				name: 'description',
				title: 'Description'
			}, {
				name: 'owner',
				title: 'Owner'			
			}, {
				name: 'createdOn',
				title: 'Created On',
				type: 'datetime'
			}, {
				name: 'scheduledOn',
				title: 'Next Execution',
				type: 'datetime'
			}, {
				name: 'status',
				title: 'Status'
			}, {
				name: 'progress',
				title: 'Progress',
				type: 'progress'
			}, {
				name: 'edit',
				type:'action',
				icon: 'pencil',
				handler: function() {
					console.log(this);
				}
			}, {
				name: 'cancel',
				type:'action',
				icon: 'remove',
				handler: function() {
					console.log(this);
				}
			}],
			rows: [{
				id: 1,
				type: 'Demographics',
				description: 'Find top 20 consumers for January 2016',
				owner: 'Yannis',
				createdOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				scheduledOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				status: 'Running',
				progress: 45
			}, {
				id: 2,
				type: 'Demographics',
				description: 'Create clusters of users based on consumption behavior patterns',
				owner: 'Yannis',
				createdOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				scheduledOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				status: 'Pending',
				progress: null
			}, {
				id: 3,
				type: 'Forecasting',
				description: 'Estimate consumption for March 2016',
				owner: 'George',
				createdOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				scheduledOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				status: 'Running',
				progress: 75
			}, {
				id: 3,
				type: 'Analysis',
				description: 'Pre aggregation of consumption for 22/02/2016',
				owner: 'System',
				createdOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				scheduledOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				status: 'Completed',
				progress: null
			}],
			pager: {
				index: 0,
				size: 1,
				count:2
			}
		};
  		
  		var data = {
			fields: [{
				name: 'id',
				title: 'Id',
				hidden: true
			}, {
				name: 'text',
				title: 'Message'
			}, {
				name: 'createdOn',
				title: 'Created On',
				type: 'datetime'
			}, {
				name: 'acknowledged',
				title: '',
				type: 'boolean',
				align: 'center'
			}],
			rows: [{
				id: 1,
				text: 'Job \'Pre aggregation of consumption for 22/02/2016\' has successfully completed',
				createdOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				acknowledged: true
			}, {
				id: 2,
				text: 'Job \'Estimate consumption for March 2016\' has started.',
				createdOn: new Date((new Date()).getTime() + Math.random() * 3600000),
				acknowledged: false
			}],
			pager: {
				index: 0,
				size: 2,
				count:2
			}
		};

		const scheduleTitle = (
			<span>
				<i className='fa fa-clock-o fa-fw'></i>
				<span style={{ paddingLeft: 4 }}>Jobs</span>
				<span style={{float: 'right',  marginTop: -3, marginLeft: 5 }}>
					<Bootstrap.Button	bsStyle="default" className="btn-circle">
						<Bootstrap.Glyphicon glyph="plus" />
					</Bootstrap.Button>
				</span>
			</span>
		);
		
		const historyTitle = (
			<span>
				<i className='fa fa-calendar fa-fw'></i>
				<span style={{ paddingLeft: 4 }}>Messages</span>
				<span style={{float: 'right',  marginTop: -3, marginLeft: 5 }}></span>
			</span>
		);

		
  		return (
			<div className="container-fluid" style={{ paddingTop: 10 }}>
				<div className="row">
					<div className="col-md-12">
						<Breadcrumb routes={this.props.routes}/>
					</div>
				</div>
				<div className="row">
					<div className='col-md-12'>
						<Bootstrap.Panel header={scheduleTitle}>
							<Bootstrap.ListGroup fill>
								<Bootstrap.ListGroupItem>	
									<Table data={jobs}></Table>
								</Bootstrap.ListGroupItem>
							</Bootstrap.ListGroup>
						</Bootstrap.Panel>
					</div>
				</div>
				<div className="row">
					<div className="col-md-12">
						<Bootstrap.Panel header={historyTitle}>
							<Bootstrap.ListGroup fill>
								<Bootstrap.ListGroupItem>	
									<Table data={data}></Table>
								</Bootstrap.ListGroupItem>
								<Bootstrap.ListGroupItem>
									<span style={{ paddingLeft : 7}}> </span>
										<Link to='/alerts' style={{ paddingLeft : 7, float: 'right'}}>View alerts</Link>
								</Bootstrap.ListGroupItem>
							</Bootstrap.ListGroup>
						</Bootstrap.Panel>
					</div>
				</div>
			</div>
 		);
  	}
});

Scheduler.icon = 'clock-o';
Scheduler.title = 'Section.Scheduler';

module.exports = Scheduler;