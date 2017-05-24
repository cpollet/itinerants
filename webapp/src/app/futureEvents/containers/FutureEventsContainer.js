import React from 'react';
import moment from 'moment';
import Events from '../screens/FutureEvents';
import {fetchFutureEvents} from '../actions';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';

class FutureEventsContainer extends React.Component {
    componentDidMount() {
        this.props.request();
    }

    render() {
        return <Events {...this.props} />;
    }
}

FutureEventsContainer.propTypes = {
    request: React.PropTypes.func.isRequired,
};

function mapStateToProps(state) {
    const isAdmin = state.app.auth.roles.indexOf('admin') > -1;
    return {
        events: ((events, availabilities) => events.map(e => ({
            id: e.eventId,
            name: e.name,
            dateTime: moment(e.dateTime),
            available: availabilities.indexOf(e.eventId) > -1,
            toPlan: !isAdmin ? false : state.app.planning.eventsToPlan.indexOf(e.eventId) > -1
        })))(state.app.futureEvents.items, state.app.availabilities.events),
        title: 'Événements à venir',
        isAdmin: isAdmin,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: () => dispatch(fetchFutureEvents()),
        plan: () => dispatch(push('/admin/plan')),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(FutureEventsContainer);
