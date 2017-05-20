import React from 'react';

class Form extends React.Component {
    render() {
        return (
            <form onSubmit={this.props.onSubmit}>
                {this.props.children}
            </form>
        );
    }
}

Form.propTypes = {
    children: React.PropTypes.node.isRequired,
    onSubmit: React.PropTypes.func
};

export default Form;
