import React from 'react';
import styles from './PasswordInput.css';

class PasswordInput extends React.Component {
    render() {
        return (
            <input className={styles.component}
                   type="password"
                   placeholder={this.props.placeholder}
                   onChange={this.props.onChange}/>
        );
    }
}

PasswordInput.propTypes = {
    placeholder: React.PropTypes.string,
    onChange: React.PropTypes.func
};

export default PasswordInput;
