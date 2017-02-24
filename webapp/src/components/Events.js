import React from 'react';
import Event from './event';
import styles from './Events.css';

class Events extends React.Component {
    constructor() {
        super();
    }

    render() {
        //console.log('render Events', this.props);
        return (
            <div>
                <h3>{this.props.when} événements</h3>
                <table className={styles.component}>
                    <thead>
                    <tr>
                        <th>O</th>
                        <th>C</th>
                        <th>Date</th>
                        <th>Événement</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.props.events.map((e, i) => {
                        return <Event key={i}
                                      event={e}
                                      available={this.props.availabilities.indexOf(e.eventId) > -1}/>;
                    })}
                    </tbody>
                </table>
            </div>
        );
    }
}

Events.propTypes = {
    when: React.PropTypes.string.isRequired,
    events: React.PropTypes.array.isRequired,
    availabilities: React.PropTypes.array.isRequired,
};

export default Events;
