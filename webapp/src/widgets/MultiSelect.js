import React from 'react';
import styles from './MultiSelect.less';

class MultiSelect extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: props.value
        };
    }

    onChange(event) {
        this.setState({value: event.target.value});
        this.props.onChange(event);
    }

    render() {
        return (
            <select multiple="true"
                    className={styles.component}
                    defaultValue={this.props.value}
                    onChange={this.onChange.bind(this)}>
                {this.props.children}
            </select>
        );
    }
}

MultiSelect.defaultProps = {
    onChange: () => {
    }
};

MultiSelect.propTypes = {
    value: React.PropTypes.any,
    items: React.PropTypes.object,
    children: React.PropTypes.arrayOf(React.PropTypes.node),
    onChange: React.PropTypes.func.isRequired
};

export default MultiSelect;
