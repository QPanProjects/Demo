import React, { Component } from 'react';
import CarCard from './CarCard';
import './Car.css';
import { Avatar } from 'antd';
import { Link } from 'react-router-dom';
import { getAvatarColor } from '../util/Colors';
import { formatDate } from '../util/Helpers';

class Car extends Component {
    render() {
        return (
            <div className="car-item">
                <div className="car-header">
                    <div className="car-detail">
                        <Link className="car-link" to={`/cars/${this.props.car.id}`}>  
                            <Avatar className="car-brand-avatar"
                                style={{ backgroundColor: getAvatarColor(this.props.car.brand)}} >
                                {this.props.car.brand[0].toUpperCase()}
                            </Avatar>
                            <span className="car-brand">
                                {this.props.car.brand}
                            </span>
                            <span className="car-model">
                                {this.props.car.model}
                            </span>
                            <span className="car-price">
                                <b>&euro;</b> {this.props.car.price}
                            </span>
                            {/* <span className="car-store">
                                @{this.props.car.storeId}
                            </span> */}
                        </Link>
                    </div>
                    <div className="car-specs">
                        Year: {this.props.car.yearOfRelease}
                        <span className="separator">.</span>
                        Fuel: {this.props.car.fuelConsumption} Km/L
                        <span className="separator">.</span>
                        Annual: <b>&euro;</b> {this.props.car.annualMaintenanceCost}
                    </div>
                </div>
                <div className="car-footer">
                    {this.props.car.createdBy.name}
                    <span className="separator">.</span>
                    {formatDate(this.props.car.creationDateTime)}
                </div>
            </div>
        );
    }
}

export default Car;