export interface UserGroup {
  idGroup: number;
  name: string;
  permissions?: GroupPermission[];
}

export interface GroupPermission {
  resource: Resource;
  permission: Permission;
}
export interface MinPermissions {
  idResource: number;
  idPermission: number;
}

export interface Resource {
  idResource: number;
  name: string;
  permissions?: Permission[];
  checked?: boolean;
}

export interface Permission {
  idPermission: number;
  name: string;
  checked?: boolean;
  parentResource?: Resource;
}

export const GUEST_GROUP = 8;
