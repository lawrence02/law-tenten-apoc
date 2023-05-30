import { Gender } from 'app/entities/enumerations/gender.model';
import { InfectionStatus } from 'app/entities/enumerations/infection-status.model';

import { ISurvivor, NewSurvivor } from './survivor.model';

export const sampleWithRequiredData: ISurvivor = {
  id: 28795,
};

export const sampleWithPartialData: ISurvivor = {
  id: 87920,
  survivorId: 'clicks-and-mortar Overpass SSL',
  name: 'virtual PNG Accounts',
  latitude: 'to B2B',
  longitude: 'Handcrafted National',
};

export const sampleWithFullData: ISurvivor = {
  id: 653,
  survivorId: 'seamless',
  name: 'Wooden Awesome silver',
  age: 69459,
  gender: Gender['Unknown'],
  latitude: 'back-end functionalities North',
  longitude: 'copying Armenian',
  status: InfectionStatus['Normal'],
};

export const sampleWithNewData: NewSurvivor = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
