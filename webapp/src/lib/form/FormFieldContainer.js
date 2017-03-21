import React from 'react';

class FormFieldContainer extends React.Component {
    onChange(event) {
        this.context.update(this.props.name, event.target.value);
    }

    render() {
        return React.cloneElement(this.props.children, {
            onChange: this.onChange.bind(this)
        });
    }
}

FormFieldContainer.propTypes = {
    children: React.PropTypes.element.isRequired,
    /**
     * The name of the wrapped field.
     */
    name: React.PropTypes.string.isRequired
};
FormFieldContainer.contextTypes = {
    /**
     * Method to use when the wrapped field is updated. This method expects to have as 1st parameter the field's name
     * and as 2nd parameter the field's value.
     */
    update: React.PropTypes.func.isRequired
};

export default FormFieldContainer;
