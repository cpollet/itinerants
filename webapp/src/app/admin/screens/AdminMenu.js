import React from 'react';
import {Link} from 'react-router';

class AdminMenu extends React.Component {
    render() {
        return (
            <div>
                {!this.props.children && <Link to={'/admin/users/create'}>Créer un itinérant</Link>}
                {this.props.children}
            </div>
        );
    }
}

AdminMenu.propTypes = {
    children: React.PropTypes.node
};

export default AdminMenu;
