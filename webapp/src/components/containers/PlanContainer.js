import React from 'react';
import {connect} from 'react-redux';
import moment from 'moment';
import {push} from 'react-router-redux';
import Planning from '../Planning';
import {fetchPlanProposal, toggleSelection} from '../../reducers/actions';

class PlanContainer extends React.Component {
    render() {
        return (
            <Planning {...this.props}/>
        );
    }

    componentDidMount() {
        this.props.request(this.props.eventIds);
    }
}

PlanContainer.propTypes = {
    request: React.PropTypes.func.isRequired,
    eventIds: React.PropTypes.array.isRequired,
};

function mapStateToProps(state) {
    return {
        eventIds: state.app.planning.eventsToPlan,
        proposal: ((proposals) => proposals.events.map(event => ({
            eventId: event.eventId,
            eventName: event.name,
            eventSize: event.eventSize,
            dateTime: moment(event.dateTime),
            selectedPeople: event.selectedPeople,
            availablePeople: event.availablePeople.map(p => ({
                personId: p,
                name: proposals.attendees[p].name,
                attendancesCount: proposals.attendees[p].attendancesCount + proposals.events.filter(e => e.selectedPeople.indexOf(p) > -1).length
            })).sort((p1, p2) => (p1.name > p2.name))
        })))(state.app.planning.proposal).sort((e1, e2) => (e1.dateTime > e2.dateTime)),
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: (eventIds) => dispatch(fetchPlanProposal(eventIds)),
        back: () => dispatch(push('/future')),
        toggleSelection: (eventId, personId) => dispatch(toggleSelection(eventId, personId))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(PlanContainer);
