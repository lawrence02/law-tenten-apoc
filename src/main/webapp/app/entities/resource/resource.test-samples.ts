import { ResourceType } from 'app/entities/enumerations/resource-type.model';

import { IResource, NewResource } from './resource.model';

export const sampleWithRequiredData: IResource = {
  id: 29528,
};

export const sampleWithPartialData: IResource = {
  id: 36799,
  quantity: 'enable Orchestrator',
};

export const sampleWithFullData: IResource = {
  id: 96574,
  resourceType: ResourceType['Food'],
  quantity: 'Avon',
};

export const sampleWithNewData: NewResource = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
