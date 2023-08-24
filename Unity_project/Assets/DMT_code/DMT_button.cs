using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class DMT_button : MonoBehaviour
{
    /// <summary>
    /// 반응속도 테스트
    /// 사용자의 반응속도 측정
    /// 잘못된 입력시 페널티 부여
    /// </summary>
    public float move_speed = 0.5f;
    
    // Start is called before the first frame update
    void Start()
    {
        
    }
    // Update is called once per frame
    void Update()
    {
        Button_press();
    }

    void Button_press()
    {            
        if (Input.GetMouseButtonDown(0)) // 마우스 클릭 시
        {
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;

            if (Physics.Raycast(ray, out hit))
            {
                if (hit.collider.CompareTag("DMT_PracticeInputButton")|| hit.collider.CompareTag("DMT_InputButton")) //이 이름의 태그를 가진 버튼 오브젝트를 눌렀을 때
                {
                        StartCoroutine(buttonpress(1.0f));
                }
            }
        }
    }

    
    IEnumerator buttonpress(float delay) //버튼 입력시 효과
    {
        transform.localPosition = new Vector3(0f, 1.2f, 0f);
        while (delay > 0.2f)
        {
            delay -= Time.deltaTime;
            yield return new WaitForFixedUpdate();
        }
        transform.localPosition = new Vector3(0f, 1.5f, 0f);
    }
}
