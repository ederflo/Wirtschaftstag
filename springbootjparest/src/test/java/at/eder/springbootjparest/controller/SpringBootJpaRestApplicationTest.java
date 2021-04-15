package at.eder.springbootjparest.controller;

import at.eder.springbootjparest.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringBootJpaRestApplicationTest {

    private ArrayList<Long> pupilIds = new ArrayList<Long>();
    private ArrayList<Long> companyIds = new ArrayList<Long>();
    private ArrayList<Long> participationIds = new ArrayList<Long>();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(0)
    void testTest() throws Exception {
        System.out.println("DEBUG: TEST");
        mockMvc.perform(
                get("/")
        ).andExpect(status().isOk());
    }
    
    @Test
    @Order(1)
    public void AA_001_getAllUsers() throws Exception {
        mockMvc.perform(get("/api/users?userType=all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void AA_002_addOneUser() throws Exception {
        Pupil pupil = Pupil.builder()
                .name("pupil1")
                .email("pupil1@pupil.at")
                .pwdToken("123")
                .build();
        MvcResult result = mockMvc.perform(post("/api/users?userType=pupil")
                .content(toJson(pupil))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
        Pupil pupilResult = objectMapper.readValue(result.getResponse().getContentAsString(), Pupil.class);
        pupilIds.add(pupilResult.getId());
    }

    @Test
    @Order(3)
    public void AA_003_getUserById() throws Exception {
        long id = 2;
        if (pupilIds.size() > 0)
            id = pupilIds.get(0);

        mockMvc.perform(get("/api/users/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(jsonPath("$.pwdToken").isNotEmpty());
    }

    @Test
    @Order(4)
    public void AA_004_updateOneUser() throws Exception {
        long id = -1;
        if (pupilIds.size() > 0)
            id = pupilIds.get(0);

        Pupil pupil = Pupil.builder()
                .id(id)
                .name("pupil2")
                .email("pupil2@pupil.at")
                .pwdToken("123456")
                .build();
        mockMvc.perform(put("/api/users?userType=pupil")
                .content(toJson(pupil))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(5)
    public void AA_005_deleteOneOfUser() throws Exception {
        long id = -1;
        if (pupilIds.size() > 0) {
            id = pupilIds.get(0);
            pupilIds.remove(0);
        }

        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    public void AB_001_getAllCompanies() throws Exception {
        mockMvc.perform(get("/api/companies/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").isNotEmpty())
                .andExpect(jsonPath("$.[*].name").isNotEmpty())
                .andExpect(jsonPath("$.[*].email").isNotEmpty())
                .andExpect(jsonPath("$.[*].phone").isNotEmpty())
                .andExpect(jsonPath("$.[*].street").isNotEmpty())
                .andExpect(jsonPath("$.[*].zipTown").isNotEmpty())
                .andExpect(jsonPath("$.[*].comments").isNotEmpty())
                .andExpect(jsonPath("$.[*].replyTo").isNotEmpty());
    }

    @Test
    @Order(7)
    public void AB_002_addOneCompany() throws Exception {
        Company company = Company.builder()
                .name("company1")
                .eMail("company1@company.at")
                .phone("+436502700878")
                .street("Straße 1")
                .zipTown("1234")
                .comments("This is a company")
                .replyTo("replyer1")
                .build();

        MvcResult result = mockMvc.perform(post("/api/companies/")
                .content(toJson(company))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
        Company comp = objectMapper.readValue(result.getResponse().getContentAsString(), Company.class);
        companyIds.add(comp.getId());
    }

    @Test
    @Order(8)
    public void AB_003_getOneCompanyById() throws Exception {
        long id = 1;
        if (companyIds.size() > 0)
            id = companyIds.get(0);

        mockMvc.perform(get("/api/companies/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(jsonPath("$.phone").isNotEmpty())
                .andExpect(jsonPath("$.street").isNotEmpty())
                .andExpect(jsonPath("$.zipTown").isNotEmpty())
                .andExpect(jsonPath("$.comments").isNotEmpty())
                .andExpect(jsonPath("$.replyTo").isNotEmpty());
    }

    @Test
    @Order(9)
    public void AB_004_updateOneCompany() throws Exception {
        long id = -1;
        if (companyIds.size() > 0)
            id = companyIds.get(0);

        Company company = Company.builder()
                .id(id)
                .name("company2")
                .street("Straße 2")
                .zipTown("5678")
                .phone("+436502700879")
                .replyTo("replyer2")
                .comments("This is a company2")
                .eMail("company2@company.at")
                .build();

        mockMvc.perform(put("/api/companies/")
                .content(toJson(company))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(10)
    public void AB_005_deleteOneCompany() throws Exception {
        long id = 5;
        if (companyIds.size() > 0) {
            id = companyIds.get(0);
            companyIds.remove(0);
        }

        mockMvc.perform(delete("/api/companies/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @Order(11)
    public void AC_001_getAllParticipations() throws Exception {
        mockMvc.perform(get("/api/participations?eventId=-1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").isNotEmpty())
                .andExpect(jsonPath("$.[*].price").isNotEmpty())
                .andExpect(jsonPath("$.[*].paidAlready").isNotEmpty())
                .andExpect(jsonPath("$.[*].comments").isNotEmpty());
    }

    @Test
    @Order(12)
    public void AC_002_addOneParticipation() throws Exception {
        Participation participation = Participation.builder()
                .price(10)
                .paidAlready(10)
                .comments("Netter typ")
                .build();

        MvcResult result = mockMvc.perform(post("/api/participations/")
                .content(toJson(participation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
        Participation participationResult = objectMapper.readValue(result.getResponse().getContentAsString(), Participation.class);
        participationIds.add(participationResult.getId());
    }

    @Test
    @Order(13)
    public void AC_003_getOneParticipationById() throws Exception {
        long id = 3;
        if (participationIds.size() > 0)
            id = participationIds.get(0);

        mockMvc.perform(get("/api/participations/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.price").isNotEmpty())
                .andExpect(jsonPath("$.paidAlready").isNotEmpty())
                .andExpect(jsonPath("$.comments").isNotEmpty());
    }

    @Test
    @Order(14)
    public void AC_004_updateOneParticipation() throws Exception {
        long id = -1;
        if (participationIds.size() > 0)
            id = participationIds.get(0);

        Participation participation = Participation.builder()
                .id(id)
                .price(100)
                .paidAlready(25)
                .comments("Der Hund zohlt imma zu spot!")
                .build();

        mockMvc.perform(put("/api/participations/")
                .content(toJson(participation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(15)
    public void AC_005_deleteOneParticipation() throws Exception {
        long id = -1;
        if (participationIds.size() > 0) {
            id = participationIds.get(0);
            participationIds.remove(0);
        }


        mockMvc.perform(delete("/api/participations/1"))
                .andExpect(status().isOk());
    }

    public static String toJson(Object o) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(o);
    }
}