import React from 'react';
import styles from './Checkbox.css';

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
            </div>
        );
    }
}

Checkbox.propTypes = {
    checked: React.PropTypes.bool.isRequired,
    disabled: React.PropTypes.bool,
    onClick: React.PropTypes.func,
};

export default Checkbox;
