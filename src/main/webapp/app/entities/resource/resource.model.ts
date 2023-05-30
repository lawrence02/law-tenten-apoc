import { ISurvivor } from 'app/entities/survivor/survivor.model';
import { ResourceType } from 'app/entities/enumerations/resource-type.model';

export interface IResource {
  id: number;
  resourceType?: ResourceType | null;
  quantity?: string | null;
  survivor?: Pick<ISurvivor, 'id'> | null;
}

export type NewResource = Omit<IResource, 'id'> & { id: null };
