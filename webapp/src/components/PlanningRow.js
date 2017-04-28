import React from 'react';
import Checkbox from './core/Checkbox';
import styles from './PlanningRow.less';

class PlanningRow extends React.Component {
    render() {
        function round(number, digits) {
            const coeff = Number('1e' + digits);
            return Math.round(number * coeff) / coeff;
        }

        function ratioToTarget(person) {
            return round(person.ratio / person.targetRatio, 2);
        }

        function ratioToTargetString(person) {
            return round(person.ratio, 2) + ':' + person.targetRatio;
        }

        return (
            <div className={styles.component}
                 onClick={this.props.onClick}>
                <div className={styles.checkboxContainer}>
                    <Checkbox checked={this.props.isSelected}/>
                </div>
                <div className={styles.label}>
                    <div>
                        {this.props.person.name}
                    </div>
                    <div>
                        {ratioToTarget(this.props.person)} ({ratioToTargetString(this.props.person)})
                    </div>
                </div>
            </div>
        );
    }
}

PlanningRow.propTypes = {
    person: React.PropTypes.object.isRequired,
    isSelected: React.PropTypes.bool.isRequired,
    onClick: React.PropTypes.func.isRequired
};

export default PlanningRow;
