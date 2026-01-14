import React from "react";
import OurTable, { ButtonColumn } from "main/components/OurTable";

import { useBackendMutation } from "main/utils/useBackend";
import { cellToAxiosParamsDelete, onDeleteSuccess } from "main/utils/urlUtils";
import { useNavigate } from "react-router";
import { hasRole } from "main/utils/useCurrentUser";

export default function UrlTable({
  urls,
  currentUser,
  testIdPrefix = "UrlTable",
}) {
  const navigate = useNavigate();

  const editCallback = (cell) => {
    navigate(`/urls/edit/${cell.row.original.id}`);
  };

  // Stryker disable all : hard to test for query caching

  const deleteMutation = useBackendMutation(
    cellToAxiosParamsDelete,
    { onSuccess: onDeleteSuccess },
    ["/api/urls/all"],
  );
  // Stryker restore all

  // Stryker disable next-line all : TODO try to make a good test for this
  const deleteCallback = async (cell) => {
    deleteMutation.mutate(cell);
  };

  const columns = [
    {
      header: "id",
      accessorKey: "id", // accessor is the "key" in the data
    },

    {
      header: "Url",
      accessorKey: "url",
    },
    {
      header: "Short Description",
      accessorKey: "shortDescription",
    },
    {
      header: "Long Description",
      accessorKey: "longDescription",
    },
  ];

  if (hasRole(currentUser, "ROLE_ADMIN")) {
    columns.push(ButtonColumn("Edit", "primary", editCallback, testIdPrefix));
    columns.push(
      ButtonColumn("Delete", "danger", deleteCallback, testIdPrefix),
    );
  }

  const urlsForTable =
    urls._embedded && urls._embedded.urls ? urls._embedded?.urls : [];

  return (
    <OurTable data={urlsForTable} columns={columns} testid={testIdPrefix} />
  );
}
