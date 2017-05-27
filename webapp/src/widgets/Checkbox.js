import React from 'react';
import styles from './Checkbox.less';

class Checkbox extends React.Component {
    render() {
        return (
            <div className={styles.component} onClick={() => {
                if (!this.props.disabled) {
                    this.props.onClick();
                }
            }}>
                <div className={styles.checkbox + (this.props.disabled ? ' ' + styles.disabled : '')}>
                    {this.props.checked &&
                    <div>
                        <div className={styles.inner}></div>
                        <div className={styles.outer}></div>
                    </div>}
                </div>
                {this.props.label || this.props.children}
            </div>
        );
    }
}

Checkbox.propTypes = {
    checked: React.PropTypes.bool.isRequired,
    disabled: React.PropTypes.bool,
    onClick: React.PropTypes.func.isRequired,
    label: React.PropTypes.node,
    children: React.PropTypes.node
};

Checkbox.defaultProps = {
    onClick: () => {
    }
};

export default Checkbox;
