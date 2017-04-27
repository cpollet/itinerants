import React from 'react';
import styles from './Checkbox.less';

class Checkbox extends React.Component {
    render() {
        return (
            <div onClick={this.props.onClick}>
                <div className={styles.component + (this.props.disabled ? ' ' + styles.disabled : '')}>
                    {this.props.checked &&
                    <div>
                        <div className={styles.inner}></div>
                        <div className={styles.outer}></div>
                    </div>}
                    <input type="checkbox"/>
                </div>
                {this.props.label}
            </div>
        );
    }
}

Checkbox.propTypes = {
    checked: React.PropTypes.bool.isRequired,
    disabled: React.PropTypes.bool,
    onClick: React.PropTypes.func,
    label: React.PropTypes.node
};

export default Checkbox;
