import React from 'react';
import EventContainer from './containers/EventContainer';
import Button from './core/Button';
import {renderIf} from '../lib/helpers';
import styles from './Events.css';

class Events extends React.Component {
    render() {
        return (
            <div className={styles.component}>
                <h2>{this.props.title}</h2>
                <table>
                    <thead>
                    <tr>
                        <th>O</th>
                        <th>C</th>
                        <th>Date</th>
                        <th>Événement</th>
                        {renderIf(this.props.isAdmin, <th>&nbsp;</th>)}
                    </tr>
                    </thead>
                    <tbody>
                    {this.props.events.map((e, i) => <EventContainer key={i} event={e} isAdmin={this.props.isAdmin}/>)}
                    </tbody>
                </table>
                {renderIf(this.props.isAdmin,
                    <div className={styles.right}>
                        <Button type="primary">Plannifier les sélectionnés</Button>
                    </div>
                )}
            </div>
        );
    }
}

Events.propTypes = {
    title: React.PropTypes.string.isRequired,
    events: React.PropTypes.array.isRequired,
    isAdmin: React.PropTypes.bool.isRequired,
};

export default Events;
