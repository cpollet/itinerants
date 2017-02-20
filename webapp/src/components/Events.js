import React from 'react';

class Events extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <h3>Événements</h3>
                {this.props.when} Events
            </div>
        );
    }
}

Events.propTypes = {
    when: React.PropTypes.string
};

export default Events;
