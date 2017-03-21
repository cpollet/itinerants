import React from 'react';
import moment from 'moment';
import Events from '../Events';
import {fetchFutureEvents} from '../../reducers/actions';
import {connect} from 'react-redux';

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
    return {
        events: ((events, availabilities) => events.map(e => ({
            id: e.eventId,
            name: e.name,
            dateTime: moment(e.dateTime),
            available: availabilities.indexOf(e.eventId) > -1,
        })))(state.app.futureEvents.items, state.app.availabilities),
        title: 'Événement futurs',
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: () => dispatch(fetchFutureEvents()),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(FutureEventsContainer);
