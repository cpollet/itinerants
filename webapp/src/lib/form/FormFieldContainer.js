import React from 'react';

class FormFieldContainer extends React.Component {
    onChange(event) {
        if (typeof event.target.selectedOptions !== 'undefined') {
            let value = [];
            for (let i = 0; i < event.target.selectedOptions.length; i++) {
                value.push(event.target.selectedOptions[i].value);
            }
            this.context.update(this.props.name, value);
        } else {
            this.context.update(this.props.name, event.target.value);
        }
    }

    componentWillMount() {
        this.context.update(this.props.name, this.context.fieldValues[this.props.name]);
    }

    render() {
        return React.cloneElement(this.props.children, {
            onChange: this.onChange.bind(this),
            value: this.context.fieldValues[this.props.name]
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
    update: React.PropTypes.func.isRequired,
    fieldValues: React.PropTypes.object
};

export default FormFieldContainer;
