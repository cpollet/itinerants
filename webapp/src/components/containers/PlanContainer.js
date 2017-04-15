import React from 'react';
import {connect} from 'react-redux';
import moment from 'moment';
import Planning from '../Planning';
import {fetchPlanProposal} from '../../reducers/actions';

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
        proposal: ((proposals) => proposals.map(proposal => ({
            eventId: proposal.eventId,
            eventName: proposal.name,
            dateTime: moment(proposal.dateTime),
            selectedPeople: proposal.selectedPeople,
            availablePeople: proposal.availablePeople
        })))(state.app.planning.proposal)
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: (eventIds) => dispatch(fetchPlanProposal(eventIds)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(PlanContainer);
