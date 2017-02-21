import React from 'react';

class Events extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <h3>Événements</h3>
                {this.props.len} {this.props.when} Events
            </div>
        );
    }
}

Events.propTypes = {
    when: React.PropTypes.string.isRequired,
    len: React.PropTypes.number
};

export default Events;
