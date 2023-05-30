import { Gender } from 'app/entities/enumerations/gender.model';
import { InfectionStatus } from 'app/entities/enumerations/infection-status.model';

export interface ISurvivor {
  id: number;
  survivorId?: string | null;
  name?: string | null;
  age?: number | null;
  gender?: Gender | null;
  latitude?: string | null;
  longitude?: string | null;
  status?: InfectionStatus | null;
}

export type NewSurvivor = Omit<ISurvivor, 'id'> & { id: null };
