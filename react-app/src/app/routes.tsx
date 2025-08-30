import { createBrowserRouter } from "react-router-dom";
import { lazy } from "react";

const AppList = lazy(() => import('../modules/list'))

const AppRouter = createBrowserRouter([
  {
    path: "/",
    element: <AppList />
  },
]);

export default AppRouter;
