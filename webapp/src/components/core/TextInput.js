import React from 'react';
import styles from './TextInput.css';

class TextInput extends React.Component {
    render() {
        return (
            <input className={styles.component}
                   type="text"
                   placeholder={this.props.placeholder}
                   value={this.props.value}
                   onChange={this.props.onChange}/>
        );
    }
}

TextInput.propTypes = {
    placeholder: React.PropTypes.string,
    value: React.PropTypes.any,
    onChange: React.PropTypes.func
};

export default TextInput;
