import React from 'react';
import styles from './Button.css';

class Button extends React.Component {
    render() {
        let htmlType = (this.props.submit ? 'submit' : 'button');
        return (
            <button type={htmlType} className={styles.component + ' ' + styles[this.props.type]} onClick={this.props.onClick}>
                {this.props.children}
            </button>
        );
    }
}

Button.propTypes = {
    children: React.PropTypes.any,
    type: React.PropTypes.oneOf(['primary']).isRequired,
    onClick: React.PropTypes.func,
    submit: React.PropTypes.bool
};

export default Button;
