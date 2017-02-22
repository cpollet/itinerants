import React from 'react';

class Events extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <h3>{this.props.when} événements</h3>
                <ul>
                    {this.props.events.map((e) => {
                        return <li key={e.eventId}>{e.name}</li>;
                    })}
                </ul>
            </div>
        );
    }
}

Events.propTypes = {
    when: React.PropTypes.string.isRequired,
    events: React.PropTypes.array.isRequired
};

export default Events;
