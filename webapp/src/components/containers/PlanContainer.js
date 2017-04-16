import React from 'react';
import {connect} from 'react-redux';
import moment from 'moment';
import {push} from 'react-router-redux';
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
            availablePeople: proposal.availablePeople.sort((p1, p2) => (p1.name > p2.name))
        })))(state.app.planning.proposal).sort((e1, e2) => (e1.dateTime > e2.dateTime)),
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: (eventIds) => dispatch(fetchPlanProposal(eventIds)),
        back: () => dispatch(push('/future'))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(PlanContainer);
