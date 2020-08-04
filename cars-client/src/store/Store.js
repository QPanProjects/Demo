import React, { Component } from 'react';
import './Store.css';
import { Avatar, Icon } from 'antd';
import { Link } from 'react-router-dom';
import { getAvatarColor } from '../util/Colors';
import { formatDateTime } from '../util/Helpers';

import { Radio, Button } from 'antd';
const RadioGroup = Radio.Group;

class Store extends Component {

    render() {
        
        return (
            <div className="store-content">
                <div className="store-header">
                    <div className="store-creator-info">
                        <Link className="creator-link" to={`/users/${this.props.store.createdBy.username}`}>
                            <Avatar className="store-creator-avatar"
                                style={{ backgroundColor: getAvatarColor(this.props.store.createdBy.name)}} >
                                {this.props.store.createdBy.name[0].toUpperCase()}
                            </Avatar>
                            <span className="store-creator-name">
                                {this.props.store.createdBy.name}
                            </span>
                            <span className="store-creator-username">
                                @{this.props.store.createdBy.username}
                            </span>
                            <span className="store-creation-date">
                                {formatDateTime(this.props.store.creationDateTime)}
                            </span>
                        </Link>
                    </div>
                    <div className="store-name">
                        {this.props.store.name}
                        <span className="separator">.</span>
                    </div>
                </div>
                <div className="store-footer">
                </div>
            </div>
        );
    }
}

export default Store;