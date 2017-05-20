import React from 'react';
import EventContainer from '../containers/EventContainer';
import Button from '../../../widgets/Button';
import {renderIf} from '../../../lib/helpers';
import styles from './FutureEvents.less';

class Events extends React.Component {
    render() {
        return (
            <div className={styles.component}>
                <h2>{this.props.title}</h2>
                {this.props.events.map((e, i) => <EventContainer key={i} event={e} isAdmin={this.props.isAdmin}/>)}

                {renderIf(this.props.isAdmin,
                    <div className={styles.right}>
                        <Button type="primary" onClick={this.props.plan}>Planifier les sélectionnés</Button>
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
    plan: React.PropTypes.func.isRequired,
};

export default Events;