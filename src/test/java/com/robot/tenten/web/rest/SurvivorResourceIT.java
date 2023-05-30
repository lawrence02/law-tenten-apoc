package com.robot.tenten.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.robot.tenten.IntegrationTest;
import com.robot.tenten.domain.Survivor;
import com.robot.tenten.domain.enumeration.Gender;
import com.robot.tenten.domain.enumeration.InfectionStatus;
import com.robot.tenten.repository.SurvivorRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SurvivorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SurvivorResourceIT {

    private static final String DEFAULT_SURVIVOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_SURVIVOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final InfectionStatus DEFAULT_STATUS = InfectionStatus.Normal;
    private static final InfectionStatus UPDATED_STATUS = InfectionStatus.Infected;

    private static final String ENTITY_API_URL = "/api/survivors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SurvivorRepository survivorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurvivorMockMvc;

    private Survivor survivor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Survivor createEntity(EntityManager em) {
        Survivor survivor = new Survivor()
            .survivorId(DEFAULT_SURVIVOR_ID)
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .gender(DEFAULT_GENDER)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .status(DEFAULT_STATUS);
        return survivor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Survivor createUpdatedEntity(EntityManager em) {
        Survivor survivor = new Survivor()
            .survivorId(UPDATED_SURVIVOR_ID)
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .status(UPDATED_STATUS);
        return survivor;
    }

    @BeforeEach
    public void initTest() {
        survivor = createEntity(em);
    }

    @Test
    @Transactional
    void createSurvivor() throws Exception {
        int databaseSizeBeforeCreate = survivorRepository.findAll().size();
        // Create the Survivor
        restSurvivorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survivor)))
            .andExpect(status().isCreated());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeCreate + 1);
        Survivor testSurvivor = survivorList.get(survivorList.size() - 1);
        assertThat(testSurvivor.getSurvivorId()).isEqualTo(DEFAULT_SURVIVOR_ID);
        assertThat(testSurvivor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSurvivor.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSurvivor.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testSurvivor.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testSurvivor.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testSurvivor.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createSurvivorWithExistingId() throws Exception {
        // Create the Survivor with an existing ID
        survivor.setId(1L);

        int databaseSizeBeforeCreate = survivorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurvivorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survivor)))
            .andExpect(status().isBadRequest());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSurvivors() throws Exception {
        // Initialize the database
        survivorRepository.saveAndFlush(survivor);

        // Get all the survivorList
        restSurvivorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(survivor.getId().intValue())))
            .andExpect(jsonPath("$.[*].survivorId").value(hasItem(DEFAULT_SURVIVOR_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getSurvivor() throws Exception {
        // Initialize the database
        survivorRepository.saveAndFlush(survivor);

        // Get the survivor
        restSurvivorMockMvc
            .perform(get(ENTITY_API_URL_ID, survivor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(survivor.getId().intValue()))
            .andExpect(jsonPath("$.survivorId").value(DEFAULT_SURVIVOR_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSurvivor() throws Exception {
        // Get the survivor
        restSurvivorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSurvivor() throws Exception {
        // Initialize the database
        survivorRepository.saveAndFlush(survivor);

        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();

        // Update the survivor
        Survivor updatedSurvivor = survivorRepository.findById(survivor.getId()).get();
        // Disconnect from session so that the updates on updatedSurvivor are not directly saved in db
        em.detach(updatedSurvivor);
        updatedSurvivor
            .survivorId(UPDATED_SURVIVOR_ID)
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .status(UPDATED_STATUS);

        restSurvivorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSurvivor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSurvivor))
            )
            .andExpect(status().isOk());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
        Survivor testSurvivor = survivorList.get(survivorList.size() - 1);
        assertThat(testSurvivor.getSurvivorId()).isEqualTo(UPDATED_SURVIVOR_ID);
        assertThat(testSurvivor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSurvivor.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSurvivor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSurvivor.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSurvivor.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSurvivor.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingSurvivor() throws Exception {
        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();
        survivor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurvivorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, survivor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(survivor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSurvivor() throws Exception {
        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();
        survivor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurvivorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(survivor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSurvivor() throws Exception {
        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();
        survivor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurvivorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(survivor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSurvivorWithPatch() throws Exception {
        // Initialize the database
        survivorRepository.saveAndFlush(survivor);

        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();

        // Update the survivor using partial update
        Survivor partialUpdatedSurvivor = new Survivor();
        partialUpdatedSurvivor.setId(survivor.getId());

        partialUpdatedSurvivor.name(UPDATED_NAME).age(UPDATED_AGE).gender(UPDATED_GENDER).latitude(UPDATED_LATITUDE);

        restSurvivorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurvivor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurvivor))
            )
            .andExpect(status().isOk());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
        Survivor testSurvivor = survivorList.get(survivorList.size() - 1);
        assertThat(testSurvivor.getSurvivorId()).isEqualTo(DEFAULT_SURVIVOR_ID);
        assertThat(testSurvivor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSurvivor.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSurvivor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSurvivor.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSurvivor.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testSurvivor.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateSurvivorWithPatch() throws Exception {
        // Initialize the database
        survivorRepository.saveAndFlush(survivor);

        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();

        // Update the survivor using partial update
        Survivor partialUpdatedSurvivor = new Survivor();
        partialUpdatedSurvivor.setId(survivor.getId());

        partialUpdatedSurvivor
            .survivorId(UPDATED_SURVIVOR_ID)
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .status(UPDATED_STATUS);

        restSurvivorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurvivor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurvivor))
            )
            .andExpect(status().isOk());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
        Survivor testSurvivor = survivorList.get(survivorList.size() - 1);
        assertThat(testSurvivor.getSurvivorId()).isEqualTo(UPDATED_SURVIVOR_ID);
        assertThat(testSurvivor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSurvivor.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSurvivor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSurvivor.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSurvivor.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSurvivor.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingSurvivor() throws Exception {
        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();
        survivor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurvivorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, survivor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(survivor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSurvivor() throws Exception {
        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();
        survivor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurvivorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(survivor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSurvivor() throws Exception {
        int databaseSizeBeforeUpdate = survivorRepository.findAll().size();
        survivor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurvivorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(survivor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Survivor in the database
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSurvivor() throws Exception {
        // Initialize the database
        survivorRepository.saveAndFlush(survivor);

        int databaseSizeBeforeDelete = survivorRepository.findAll().size();

        // Delete the survivor
        restSurvivorMockMvc
            .perform(delete(ENTITY_API_URL_ID, survivor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Survivor> survivorList = survivorRepository.findAll();
        assertThat(survivorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
