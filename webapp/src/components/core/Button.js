import React from 'react';
import styles from './Button.css';

class Button extends React.Component {
    render() {
        const htmlType = (this.props.submit ? 'submit' : 'button');
        const enabled = (this.props.enabled === true);
        return (
            <button type={htmlType}
                    className={styles.component + ' ' + styles[this.props.type]}
                    disabled={!enabled}
                    onClick={this.props.onClick}>
                {this.props.children}
            </button>
        );
    }
}

Button.propTypes = {
    children: React.PropTypes.any,
    type: React.PropTypes.oneOf(['primary', 'default']).isRequired,
    onClick: React.PropTypes.func,
    submit: React.PropTypes.bool,
    enabled: React.PropTypes.bool,
};

Button.defaultProps = {
    enabled: true
};

export default Button;
