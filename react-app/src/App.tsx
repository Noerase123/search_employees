import { RouterProvider } from "react-router-dom";
import AppRouter from "./app/routes";
import { Suspense } from "react";
import AppLoader from "./components/AppLoader";
import { ClientProvider } from "./app/providers/QueryClientProvider";

function App() {
  return (
    <ClientProvider>
      <Suspense fallback={<AppLoader />}>
        <RouterProvider router={AppRouter} />
      </Suspense>
    </ClientProvider>
  );
}

export default App;
