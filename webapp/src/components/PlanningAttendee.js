import React from 'react';
import styles from './PlanningAttendee.css';

class PlanningAttendee extends React.Component {
    render() {
        function round(number, digits) {
            const coeff = Number('1e' + digits);
            return Math.round(number * coeff) / coeff;
        }

        const title = this.props.count + ' participations, ratio : ' + round(this.props.ratio, 2);
        const ratioToTargetRatio = round(this.props.ratio / this.props.targetRatio, 2);

        return (
            <span className={styles.component} title={title}>
                {this.props.name} ({ratioToTargetRatio})
            </span>
        );
    }
}

PlanningAttendee.propTypes = {
    name: React.PropTypes.string.isRequired,
    count: React.PropTypes.number.isRequired,
    ratio: React.PropTypes.number.isRequired,
    targetRatio: React.PropTypes.number.isRequired,
};

export default PlanningAttendee;
