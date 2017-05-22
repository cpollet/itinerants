import React from 'react';
import {Link} from 'react-router';

class AdminMenu extends React.Component {
    render() {
        return (
            <div>
                {!this.props.children &&
                <ul>
                    <li><Link to={'/admin/users/create'}>Create user</Link></li>
                </ul>
                }
                {this.props.children}
            </div>
        );
    }
}

AdminMenu.propTypes = {
    children: React.PropTypes.node
};

export default AdminMenu;
