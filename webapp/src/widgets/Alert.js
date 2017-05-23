import React from 'react';
import styles from './Alert.css';

class Alert extends React.Component {
    render() {
        return (
            <div className={styles.component + ' ' + styles[this.props.type]}>
                <strong><i className={styles.icon}/> {this.props.title}</strong>
                &nbsp;
                {this.props.text || this.props.children}
            </div>
        );
    }
}

Alert.propTypes = {
    text: React.PropTypes.string,
    children: React.PropTypes.node,
    title: React.PropTypes.string,
    type: React.PropTypes.oneOf(['success', 'info', 'warn', 'error']).isRequired,
};

export default Alert;
