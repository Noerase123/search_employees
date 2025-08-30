/* eslint-disable @typescript-eslint/no-explicit-any */

import type { Employee } from "../types/Employee";

export type TDataTableProps = {
  data: any;
  loading?: boolean;
  setPage?: (page: number) => void;
  setSearch?: (search: string) => void;
};

const DataTable = ({
  data,
  setSearch,
  setPage,
  loading,
}: TDataTableProps) => {

  const content = data?.content as Employee[];
  const pageNumber = data?.pageable?.pageNumber;
  const currentPage = pageNumber + 1;
  const totalPages = data?.totalPages;
  const totalElements = data?.totalElements;

  return (
    <div className="max-w-5xl mx-auto p-4 bg-white relative">
      {loading && (
        <div className="absolute inset-0 flex items-center justify-center bg-white/70 dark:bg-black/50 z-10">
          <span className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-800 dark:border-white"></span>
        </div>
      )}
      <input
        type="text"
        placeholder="Search by name..."
        className="mb-4 p-2 border border-gray-300 rounded w-full"
        onChange={(e) => setSearch?.(e.target.value)}
      />
      <table className="min-w-full border border-gray-300 text-sm">
        <thead className="bg-gray-100">
          <tr>
            <th className="text-left p-2 border-b">Employee ID</th>
            <th className="text-left p-2 border-b">Name</th>
            <th className="text-left p-2 border-b">Project</th>
            <th className="text-left p-2 border-b">Skill sets</th>
            <th className="text-left p-2 border-b">Hobbies</th>
          </tr>
        </thead>
        <tbody>
          {content?.map((item) => (
            <tr key={item.id} className="hover:bg-gray-50">
              <td className="p-2 border-b">{item.employeeId}</td>
              <td className="p-2 border-b">{item.name}</td>
              <td className="p-2 border-b">{item.project}</td>
              <td className="p-2 border-b">{item.skills?.join(', ')}</td>
              <td className="p-2 border-b">{item.hobbies?.join(', ')}</td>
            </tr>
          ))}
          {content?.length === 0 && (
            <tr>
              <td colSpan={3} className="p-2 text-center text-gray-500">
                No results found
              </td>
            </tr>
          )}
        </tbody>
      </table>

      <div className="flex justify-center items-center mt-4 gap-2">
        <button
          onClick={() => setPage?.(pageNumber - 1)}
          className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
          disabled={currentPage === 1}
        >
          Prev
        </button>
        <span className="text-sm">
          Page {currentPage} of {totalPages} total {totalElements}
        </span>
        <button
          onClick={() => setPage?.(pageNumber + 1)}
          className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
          disabled={currentPage === totalPages}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default DataTable;
