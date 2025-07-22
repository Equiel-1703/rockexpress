import { Outlet } from 'react-router-dom';
import MiniHeader from './MiniHeader';

function MiniHeaderLayout() {
    return (
        <>
            <MiniHeader />
            <main>
                <Outlet />
            </main>
        </>
    );
}

export default MiniHeaderLayout;