import React from 'react';
import styles from './TextInput.css';

class TextInput extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: this.hasValue(props.value) ? props.value : ''
        };
    }

    hasValue(v) {
        return typeof v !== 'undefined' && v !== null;
    }

    onChange(event) {
        this.setState({value: event.target.value});
        this.props.onChange(event);
    }

    render() {
        return (
            <input className={styles.component}
                   type="text"
                   placeholder={this.props.placeholder}
                   value={this.state.value}
                   onChange={this.onChange.bind(this)}/>
        );
    }
}

TextInput.defaultProps = {
    onChange: () => {
    }
};

TextInput.propTypes = {
    placeholder: React.PropTypes.string,
    value: React.PropTypes.any,
    onChange: React.PropTypes.func.isRequired
};

export default TextInput;
