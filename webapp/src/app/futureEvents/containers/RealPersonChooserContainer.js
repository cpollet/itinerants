import React from 'react';
import {connect} from 'react-redux';
import {fetchFutureEvents} from '../actions';

class RealPersonChooserContainer extends React.Component {
    render() {
        return (
            <select onChange={(e) => this.props.fetch(e.target.value)} value={this.props.personId}>
                {this.props.people
                    .sort((p1, p2) => p1.name.localeCompare(p2.name))
                    .map(p => <option key={p.personId} value={p.personId}>{p.name}</option>)}
            </select>
        );
    }
}

RealPersonChooserContainer.propTypes = {
    personId: React.PropTypes.string.isRequired,
    people: React.PropTypes.array.isRequired,
    fetch: React.PropTypes.func.isRequired,
};

function mapDispatchToProps(dispatch) {
    return {
        fetch: (personId) => dispatch(fetchFutureEvents(personId)),
    };
}

function mapStateToProps(state) {
    return {
        personId: state.app.futureEvents.realPersonId,
        people: state.app.admin.people,
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(RealPersonChooserContainer);
