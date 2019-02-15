import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { Grid, Col, Row } from './FlexboxGrid';

const Bar = styled(Grid)`
	background-color: #f3f3f4;
	border-bottom: 1px solid #c2cdd8;
`;

const NavLink = styled(Link)`
	margin-left: 0.5rem;
	margin-right: 1.5rem;
	margin-top: 1rem;
	margin-bottom: 1.2rem;
`;

const Frame = styled(Grid)`
	width: 800px;
`;

const Navbar = () => (
	<Bar>
		<Frame>
			<Row>
				<NavLink to="/">Home</NavLink>
				<NavLink to="/about">About</NavLink>
				<NavLink to="/topics">Topics</NavLink>
				<NavLink to="/posts">Posts</NavLink>
			</Row>
		</Frame>
	</Bar>
);

export default Navbar;
