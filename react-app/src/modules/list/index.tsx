/* eslint-disable @typescript-eslint/no-explicit-any */
import { useQuery } from "@tanstack/react-query";
import axiosInstance from "../../api";
import { useEffect, useState } from "react";
import DataTable from "../../components/DataTable";
import { debounce } from 'lodash';

const fetchEmployees = async (search: string, page: number) => {
  const response = await axiosInstance.get("/employees", {
    params: {
      page,
      size: 5,
      sort: "id,asc",
      employeeId: search,
      name: search,
      project: search,
      hobbies: search,
      skills: search,
      matchAny: true,
      requireAllHobbies: false,
      requireAllSkills: false
    },
  });
  return response.data;
};

const ModuleList = () => {
  const [content, setContent] = useState<any>([]);
  const [search, setSearch] = useState<string>("");
  const [page, setPage] = useState<number>(0);

  const { data, isLoading } = useQuery({
    queryKey: ["employees", search, page],
    queryFn: () => fetchEmployees(search, page),
  });

  const handleDebounceSearch = debounce((value: string) => {
    setPage(0)
    setSearch(value);
  }, 500);

  useEffect(() => {
    setContent(data);
  }, [data]);

  return (
    <DataTable
      data={content}
      setSearch={handleDebounceSearch}
      setPage={setPage}
      loading={isLoading}
    />
  );
};

export default ModuleList;
