import React from 'react';
import EventContainer from '../containers/EventContainer';
import styles from './Events.css';

class Events extends React.Component {
    render() {
        return (
            <div>
                <h3>{this.props.title}</h3>
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
                    {this.props.events.map((e, i) => <EventContainer key={i} event={e}/>)}
                    </tbody>
                </table>
            </div>
        );
    }
}

Events.propTypes = {
    title: React.PropTypes.string.isRequired,
    events: React.PropTypes.array.isRequired,
};

export default Events;
