import React from 'react';
import Checkbox from '../../../widgets/Checkbox';
import Circle from '../../../icons/Circle';
import styles from './PlanningRow.less';

class PlanningRow extends React.Component {
    render() {
        function round(number, digits) {
            const coeff = Number('1e' + digits);
            return Math.round(number * coeff) / coeff;
        }

        function ratioToTarget(person) {
            if (person.targetRatio === 0) {
                return 'INF';
            }

            return round(person.ratio / person.targetRatio, 2);
        }

        function ratioToTargetString(person) {
            return round(person.ratio, 2) + ':' + person.targetRatio;
        }

        function ratioColor(ratio) {
            if (ratio === 'INF' || ratio > 1) {
                return 'red';
            } else if (ratio === 1) {
                return 'green';
            } else {
                return 'orange';
            }
        }

        const ratio = ratioToTarget(this.props.person);

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
                        <span className={styles.light}><Circle color={ratioColor(ratio)}/></span>
                        <span title="ratio : target">{ratioToTargetString(this.props.person)}</span>
                        &nbsp;&rarr;&nbsp;
                        <span title="ratio / target">{ratio === 'INF' ? '\u221E' : ratio}</span>
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
