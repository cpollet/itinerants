import React from 'react';
import Event from './event';

class Events extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <h3>{this.props.when} événements</h3>
                {this.props.events.map((e, i) => {
                    return <Event key={i} event={e}/>;
                })}
            </div>
        );
    }
}

Events.propTypes = {
    when: React.PropTypes.string.isRequired,
    events: React.PropTypes.array.isRequired
};

export default Events;
