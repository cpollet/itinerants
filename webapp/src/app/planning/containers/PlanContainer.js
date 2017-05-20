import React from 'react';
import {connect} from 'react-redux';
import moment from 'moment';
import {push} from 'react-router-redux';
import Planning from '../screens/Planning';
import {fetchPlanProposal, savePlan, toggleSelection} from '../actions';

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
    function attendancesCount(proposals, person) {
        return proposals.attendees[person].pastAttendancesCount + proposals.events.filter(e => e.selectedPeople.indexOf(person) > -1).length;
    }

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
                attendancesCount: attendancesCount(proposals, p),
                ratio: attendancesCount(proposals, p) / (proposals.pastEventsCount + proposals.events.length),
                targetRatio: proposals.attendees[p].targetRatio,
            })).sort((p1, p2) => (p1.name > p2.name))
        })))(state.app.planning.proposal).sort((e1, e2) => (e1.dateTime > e2.dateTime)),
        saving: state.app.planning.sync.pending,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: (eventIds) => dispatch(fetchPlanProposal(eventIds)),
        back: () => dispatch(push('/future')),
        save: () => dispatch(savePlan()),
        toggleSelection: (eventId, personId) => dispatch(toggleSelection(eventId, personId))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(PlanContainer);
