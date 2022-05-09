package com.funin.todo.presentation.api.todo

import com.fasterxml.jackson.databind.ObjectMapper
import com.funin.todo.ActiveTestProfile
import com.funin.todo.domain.todo.State
import com.funin.todo.generateSimpleString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveTestProfile
@AutoConfigureMockMvc
@Transactional
class TodoControllerTest {

    @Autowired
    lateinit var todoController: TodoController

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun Todo_create_success() {
        // given
        val content = generateSimpleString()
        val reason = generateSimpleString()
        val state = State.NONE
        val canUpdate = false
        val todoRequest = TodoRequest(content, state, reason, canUpdate)

        //then
        mockMvc
            .perform(
                post("/api/v1/todo")
                    .contentType(objectMapper.writeValueAsString(todoRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.data.todoId").exists())
            .andExpect(jsonPath("\$.data.authorId").exists())
            .andExpect(jsonPath("\$.data.content").exists())
            .andExpect(jsonPath("\$.data.state").exists())
            .andExpect(jsonPath("\$.data.reason").exists())
            .andExpect(jsonPath("\$.data.canUpdate").exists())
            .andExpect(jsonPath("\$.data.createdAt").exists())
    }
}