import React from 'react';
import Event from './Event';
import styles from './Events.css';

class Events extends React.Component {
    constructor() {
        super();
    }

    render() {
        //console.log('render Events', this.props);
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
                    {this.props.events.map((e, i) => <Event key={i} event={e}/>)}
                    </tbody>
                </table>
                <button onClick={this.props.request}>refresh</button>
                <button onClick={this.props.reset}>reset</button>
            </div>
        );
    }
}

Events.propTypes = {
    title: React.PropTypes.string.isRequired,
    events: React.PropTypes.array.isRequired,

    request: React.PropTypes.func.isRequired,
    reset: React.PropTypes.func.isRequired,
};

export default Events;
