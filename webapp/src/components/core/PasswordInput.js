import React from 'react';
import styles from './PasswordInput.css';

class PasswordInput extends React.Component {
    render() {
        return (
            <input className={styles.component} type="password" placeholder={this.props.placeholder}/>
        );
    }
}

PasswordInput.propTypes = {
    placeholder: React.PropTypes.string
};

export default PasswordInput;
